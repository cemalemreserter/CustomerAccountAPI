import React, { useState } from "react";
import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

export default function CurrentAccountApp() {
  const [customerId, setCustomerId] = useState("");
  const [initialCredit, setInitialCredit] = useState("");
  const [customerData, setCustomerData] = useState(null);

  const openAccount = async () => {
    try {
      await axios.post(`${API_BASE_URL}/accounts/open`, {
        customerId,
        initialCredit: parseFloat(initialCredit),
      });
      alert("Account opened successfully");
    } catch (error) {
      alert("Error opening account");
    }
  };

  const fetchCustomerDetails = async () => {
    try {
      const response = await axios.get(`${API_BASE_URL}/accounts/${customerId}`);
      setCustomerData(response.data);
    } catch (error) {
      alert("Error fetching customer details");
    }
  };

  return (
    <div className="p-6 max-w-md mx-auto bg-white rounded-xl shadow-md space-y-4">
      <h1 className="text-xl font-bold">Current Account API</h1>
      <input
        type="text"
        placeholder="Customer ID"
        value={customerId}
        onChange={(e) => setCustomerId(e.target.value)}
        className="border p-2 w-full rounded"
      />
      <input
        type="number"
        placeholder="Initial Credit"
        value={initialCredit}
        onChange={(e) => setInitialCredit(e.target.value)}
        className="border p-2 w-full rounded"
      />
      <button onClick={openAccount} className="bg-blue-500 text-white p-2 rounded w-full">
        Open Account
      </button>
      <button onClick={fetchCustomerDetails} className="bg-green-500 text-white p-2 rounded w-full">
        Get Customer Details
      </button>
      {customerData && (
        <div className="mt-4 p-4 border rounded">
          <h2 className="font-semibold">Customer Details</h2>
          <p>Name: {customerData.name}</p>
          <p>Surname: {customerData.surname}</p>
          <p>Balance: ${customerData.balance}</p>
          <h3 className="font-semibold mt-2">Transactions:</h3>
          <ul>
            {customerData.transactions.map((tx, index) => (
              <li key={index}>${tx.amount} on {tx.date}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}
