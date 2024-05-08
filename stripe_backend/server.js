const express = require('express');
const morgan = require('morgan');
const path = require('path');
const mongoose = require('mongoose');
const app = express();
const mongoURI = "mongodb+srv://celal2344:O7hYizwth6Fuk40I@ciklet.rk9ql0z.mongodb.net/?retryWrites=true&w=majority&appName=ciklet";
const bodyParser = require('body-parser')
const stripe = require('stripe')('sk_test_51OxUL7Ak8ppQVwDwUbY0H0kKTIqnuJpIb1JsFRBoP4avMEomwFs62IWlMr4imyBQ2RoZP1mmWGhub7Orc73sX0nJ00H6pgCMfc');

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

app.post('/create-payment-method', async (request, response) => {
  const paymentMethod = await stripe.paymentMethods.create({
    type: 'card',
    card: {token : "tok_visa"},
  });
  const attachedPaymentMethod = await stripe.paymentMethods.attach(
    paymentMethod.id,
    {
      customer: request.body.customerId,
    }
  );
  return response.send(paymentMethod);
});
app.post('/get-payment-method-list', async (request, response) => {
  const { customerId } = request.body;
  const paymentMethods = await stripe.customers.listPaymentMethods(
    customerId,
    {
      limit: 50,
    }
  );
  return response.send(paymentMethods);
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