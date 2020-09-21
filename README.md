# pagination-poc
This is a simple POC to demonstrate Server Side Pagination (and sorting) with Vue.js (Quasar) int the Frontend and Kotlin + Spring in the Backend

## Backend
Using Kotlin and Spring (pageable API)

Endpoints for:
- generated mocked clients and transactions (in memory)
- get clientes paginated
- get client transactions paginated

## Frontend

Using Vue.js and Quasar q-table component.

- Server side pagination with q-table component, displays clients, each page fetches the server.
- "Sub-pagination" for transactions, expand the client rows and fetch in the server the transactions and display a sub-table for each client.


