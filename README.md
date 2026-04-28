# SecureBytes

SecureBytes is a Java Swing desktop application for password-based file encryption and decryption.

## Features
- Encrypt any file using AES-256 (CBC mode with PKCS5 padding).
- Derive encryption keys from a password using PBKDF2 (HMAC-SHA256).
- Store required salt and IV alongside encrypted payload for later recovery.
- Responsive UI with background processing for file operations.

## Project Structure
- `src/com/vault/Dashboard.java` - Swing UI and file handling flow.
- `src/com/vault/CryptoEngine.java` - cryptographic operations and key derivation.

## Requirements
- Java 8 or newer (JDK recommended).

## Compile and Run
From the project root:

```bash
javac -d bin src/com/vault/*.java
java -cp bin com.vault.Dashboard
```

## Usage
1. Launch the app.
2. Choose **Encrypt File** or **Decrypt File**.
3. Select the target file.
4. Enter a password when prompted.
5. Find output next to the original file:
   - Encrypted files end with `.vault`
   - Decrypted files end with `_recovered`

## Security Notes
- Use strong, unique passwords for sensitive files.
- Do not lose the password; encrypted files cannot be recovered without it.
- This project is for learning/demo use and should be security-reviewed before production use.
