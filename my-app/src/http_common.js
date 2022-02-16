import axios from "axios";

export const urlBackend="http://localhost:8081/";

export default axios.create({
  baseURL: urlBackend,
  headers: {
    "Content-type": "application/json"
  }
});