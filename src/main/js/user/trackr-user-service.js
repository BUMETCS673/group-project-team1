/**
 * User service to abstract the logic to interact with the user API.
 *
 * @author Jean Dorancy
 */

import axios from "axios";

const REGISTRATION_PATH = "/register";
const LOGIN_PATH = "/login";
const LOGOUT_PATH = "/logout";

class TrackrUserService {
  /**
   * This check if we have a user logged by looking at local storage. No sensitive information is stored there.
   * Note: JWT is **not** accessible from JavaScript and not sored in localStorage to prevent XSS attack.
   *
   * @returns {boolean}
   */
  static isAuthenticated() {
    return localStorage.getItem("user") != null;
  }

  /**
   * Set a flag in local storage, although we may add user first and last name later
   */
  static authenticate() {
    localStorage.setItem("user", JSON.stringify({ loggedIn: true }));
  }

  /**
   * Create a user.
   *
   * @param user User object with all the attributes.
   * @returns {Promise<AxiosResponse<any>>} Promise
   */
  create = (user) => {
    return axios.post(REGISTRATION_PATH, user);
  };

  /**
   * Get JWT token by sending user credentials.
   *
   * @param credentials User credentials
   * @returns {Promise<AxiosResponse<any>>} Promise
   */
  login = (credentials) => {
    return axios.post(LOGIN_PATH, credentials);
  };

  /**
   * Send logout request to the server.
   *
   * @returns {Promise<AxiosResponse<any>>} Promise
   */
  logout = () => {
    localStorage.removeItem("user");
    return axios.get(LOGOUT_PATH);
  };
}

export default TrackrUserService;
