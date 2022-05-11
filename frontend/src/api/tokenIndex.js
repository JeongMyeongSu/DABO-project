import axios from "axios";
import { API_BASE_URL } from "../config";

function tokenInstance() {
    const instance = axios.create({
      baseURL: API_BASE_URL,
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${localStorage.getItem('accessToken')}`
      }
    });
    return instance;
  }

  export { tokenInstance };