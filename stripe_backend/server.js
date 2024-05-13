const express = require('express');
const morgan = require('morgan');
const path = require('path');
const app = express();
const bodyParser = require('body-parser')
const stripe = require('stripe')('STRIPE_API_KEY');
const crypto = require('crypto');
const fs = require('fs');

app.use('/css', express.static(path.join(__dirname, 'node_modules/bootstrap/dist/css')))
app.use('/js', express.static(path.join(__dirname, 'node_modules/bootstrap/dist/js')))
app.use('/js', express.static(path.join(__dirname, 'node_modules/jquery/dist')))
app.use(express.urlencoded({extended:true}));
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: false }))
app.use(morgan('dev'));
app.use(express.static("./public"));


var ip = require("ip");
console.dir ( ip.address );

const YOUR_DOMAIN = ip.address();

function decrypt(cipherText) {
  const secretKey = Buffer.from('1234567890123456', 'utf8');
  const iv = Buffer.alloc(16, 0); 
  try {
    const decipher = crypto.createDecipheriv('aes-128-cbc', secretKey, iv);
    let decrypted = decipher.update(cipherText, 'base64', 'utf8');
    decrypted += decipher.final('utf8');
    return decrypted;
  } catch (error) {
    console.error('Error decrypting:', error);
    return null;
  }
}
app.post('/create-payment-method', async (request, response) => {
  const { customerId,encryptedData } = request.body;
  const decryptedCardDetails = decrypt(encryptedData);
  const decryptedCardDetailsArray = decryptedCardDetails.split(' ');
  var token = "";
  var jsonData = null;
  fs.readFile('test_cards.json', 'utf8', (err, data) => 
  {
    if (err) {
      console.error(err)
      return
    }
    jsonData = JSON.parse(data);
    jsonData.cards.forEach(card => {
      if(card.number == decryptedCardDetailsArray[0]){
        token = card.token;
        createPaymentMethod(customerId,token,response)
      };
    });
  });
  console.log(token);
  console.log(decryptedCardDetailsArray)
  
});
async function createPaymentMethod(customerId,token,response){
  try {
    const paymentMethodCreate = await stripe.paymentMethods.create({
      type: 'card',
      card:{
        token: token
      }
    });
    const paymentMethod = await stripe.paymentMethods.attach(
      paymentMethodCreate.id,
      {
        customer: customerId,
      }
    );
    console.log(paymentMethod);
    return response.send(paymentMethod);
  } catch (error) {
    console.error('Error occurred:', error.message);
    return response.status(500).send('Internal Server Error');
  }
}
app.post('/get-payment-method-list', async (request, response) => {
  const { customerId } = request.body; // Extract customerId from request body
  try {
    const paymentMethods = await stripe.customers.listPaymentMethods(customerId, { limit: 50 });
    return response.send(paymentMethods);
  } catch (error) {
    console.error('Error occurred:', error.message);
    return response.status(500).send('Internal Server Error');
  }
});

app.post('/pay', async (request, response) => {
  try {
    let customerId = request.body.customerId;
    let intent = null;
    const paymentMethods = await stripe.customers.listPaymentMethods(//get payment methods of customer
      customerId,
      {
        limit: 100,
      }
    );
    let customerPaymentMethods = paymentMethods.data;
    for (let pm of customerPaymentMethods) {
      if(pm.id == request.body.paymentMethodId){//check if the payment method belongs to the customer
        intent = await createPaymentIntent(customerId,request.body.paymentMethodId,request.body.paymentAmount);//if the payment method belongs to the customer start payment intent process
        break;
      }
    };
    return generateResponse(response, intent);
  } catch (e) {
    console.log(e);
  }
});
async function createPaymentIntent(customerId, paymentMethodId,paymentAmount) {
  // Create the PaymentIntent
  let intent = await stripe.paymentIntents.create({
    amount: paymentAmount*100,
    currency: 'usd',
    payment_method: paymentMethodId,
    confirm: true,
    customer: customerId.toString(),
    automatic_payment_methods : {
      enabled: true,
      allow_redirects: "never"
    }
  });
  return intent;
}
function generateResponse(response, intent) {
  if (intent.status === 'succeeded') {
    // Handle post-payment fulfillment
    return response.send({ success: true });
  } else {
    // Any other status would be unexpected, so error
    return response.status(500).send({error: 'Unexpected status ' + intent.status});
  }
}


// app.listen(4242, () => console.log('Running on port 4242'));
app.listen(4242, YOUR_DOMAIN ,() => {
  console.log(`Listening to requests on ${YOUR_DOMAIN}:${4242}`);
});
