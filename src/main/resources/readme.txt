HMAC (Hash-based Message Authentication Code) is a mechanism for message integrity
 and authentication. When using HMAC in a Spring Boot application, you typically want
  to ensure the authenticity and integrity of requests.

🔑 How HMAC Authentication Works
Client Preparation:

Generates a unique message (e.g., JSON request) and a timestamp.
Concatenates the timestamp and the message.
Signs the concatenated string with a secret key using a hash function (e.g., HMAC-SHA256).
Sends the message, timestamp, and signature to the server.
Server Verification:

Concatenates the received message and timestamp.
Uses the same secret key to generate its own signature.
Compares its signature with the one sent by the client. If they match, the request is
considered authentic.

Explanation
HmacUtil: Handles HMAC signature generation.
HmacAuthFilter: Filters requests, validates signature and timestamp.
WebConfig: Registers the filter for specific endpoints.
HmacClient: Simulates a client generating a valid HMAC request.