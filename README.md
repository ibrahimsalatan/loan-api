# Loan API

## How to Run
1. Clone the repository.
2. Run `mvn spring-boot:run`.
3. Access the Swagger at `http://localhost:8080/swagger-ui/index.html` with username `admin` and password `password` to access the endpoints.

## Endpoints
- `POST /loans`: Create a new loan.
- `GET /loans/customers/{customerId}`: List loans for a customer.
- `GET /loans/{loanId}/installments`: List installments for a loan.
- `POST /loans/{loanId}/pay`: Pay installments for a loan.

## Authentication
- Use username `admin` and password `password` to access the endpoints.