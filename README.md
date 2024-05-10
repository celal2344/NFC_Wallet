Used Express for the server-side of the project. Server connects to the Stripe API using Stripe's Node.js SDK. There are 3 routes on the server-side. 
Whenever the user enters their card information the information gets encrypted on client-side using the AES encryption system then the encrypted message is sent to the server side using the '/create-payment-method' route and gets decrypted and sent to the Stripe API.

<img src="https://github.com/celal2344/NFC_Wallet/assets/69896844/080567c6-8464-4df9-83b6-d381d5d255a9" height="400">

Stripe API adds the payment method to customers payment methods.

<img src="https://github.com/celal2344/NFC_Wallet/assets/69896844/1b999046-cc6b-415f-a37f-49ac10d459ed" height="400">

Cards list on the main page always shows updated cards list that's been received from the API using the '/get-payment-method-list' route. All of these cards are test cards provided by the API so no real money is moved on the application.

<img src="https://github.com/celal2344/NFC_Wallet/assets/69896844/2d1df758-bf57-4f10-8225-537b5d6a23aa" height="400">

To start a payment trader should enter the payment amount to the reader app. When trader enters a payment amount and taps the pay button application starts searching for nfc tags and opens a page that waits for an nfc tag. 
When the nfc tag is received "onTagDiscovered" function initiates on the reader app and sends an APDU to the the writer then "processCommandApdu" function of the writer apps HCE class activates and returns the appropriate response to the reader.
There is only 1 AID code inside the APDU that's been registered to the both applications so reader app won't read any other nfc writer.

<img src="https://github.com/celal2344/NFC_Wallet/assets/69896844/2c884bd4-1a06-4c33-b381-ee99adf17dd0" width="400"><img src="https://github.com/celal2344/NFC_Wallet/assets/69896844/a18b25c4-82a4-49aa-a846-90ca20374770" width="400">

When the AID is confirmed on from the tag that has been received from the writer app using the isoDep.transceive method same function gets the customer ID and payment method ID from the tag and sends it to the server-side. 
Server sends these 2 information to the API to handle the rest of the payment process. 
