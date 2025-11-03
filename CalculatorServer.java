import java.io.*;
import java.net.*;
public class CalculatorServer {
    public static void main(String[] args) {
        int port = 5002;
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Calculator Server started on port " + port);
            System.out.println("Waiting for client connection...");
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );
            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true
            );
            String request;
            while (true) {
                request = in.readLine();
                if (request == null || request.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected.");
                    break;
                }
                System.out.println("Request: " + request);
                String result = processCalculation(request);
                out.println(result);
                System.out.println("Result: " + result);
            }
            clientSocket.close();
            System.out.println("Connection closed.");

        } catch (IOException e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }
    private static String processCalculation(String request) {
        try {
            String[] parts = request.split(" ");

            if (parts.length < 2) {
                return "ERROR: Invalid format";
            }
            String operation = parts[0].toUpperCase();
            switch (operation) {
                case "ADD":
                    if (parts.length != 3) return "ERROR: ADD requires 2 numbers";
                    return String.valueOf(
                            Double.parseDouble(parts[1]) + Double.parseDouble(parts[2])
                    );

                case "SUB":
                    if (parts.length != 3) return "ERROR: SUB requires 2 numbers";
                    return String.valueOf(
                            Double.parseDouble(parts[1]) - Double.parseDouble(parts[2])
                    );

                case "MUL":
                    if (parts.length != 3) return "ERROR: MUL requires 2 numbers";
                    return String.valueOf(
                            Double.parseDouble(parts[1]) * Double.parseDouble(parts[2])
                    );

                case "DIV":
                    if (parts.length != 3) return "ERROR: DIV requires 2 numbers";
                    double divisor = Double.parseDouble(parts[2]);
                    if (divisor == 0) return "ERROR: Division by zero";
                    return String.valueOf(
                            Double.parseDouble(parts[1]) / divisor
                    );

                case "POW":
                    if (parts.length != 3) return "ERROR: POW requires 2 numbers";
                    return String.valueOf(
                            Math.pow(Double.parseDouble(parts[1]), Double.parseDouble(parts[2]))
                    );

                case "SIN":
                    if (parts.length != 2) return "ERROR: SIN requires 1 number";
                    return String.valueOf(
                            Math.sin(Math.toRadians(Double.parseDouble(parts[1])))
                    );

                case "COS":
                    if (parts.length != 2) return "ERROR: COS requires 1 number";
                    return String.valueOf(
                            Math.cos(Math.toRadians(Double.parseDouble(parts[1])))
                    );

                case "TAN":
                    if (parts.length != 2) return "ERROR: TAN requires 1 number";
                    return String.valueOf(
                            Math.tan(Math.toRadians(Double.parseDouble(parts[1])))
                    );

                case "LOG":
                    if (parts.length != 2) return "ERROR: LOG requires 1 number";
                    return String.valueOf(
                            Math.log10(Double.parseDouble(parts[1]))
                    );
                default:
                    return "ERROR: Unknown operation '" + operation + "'";
            }
        } catch (NumberFormatException e) {
            return "ERROR: Invalid number format";
        } catch (Exception e) {
            return "ERROR: " + e.getMessage();
        }
    }
}