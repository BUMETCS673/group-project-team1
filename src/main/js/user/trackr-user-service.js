/**
 * User service to abstract the logic to interact with the user API.
 *
 * @author Jean Dorancy
 */

import axios from "axios";
import Cookies from "js-cookie";

const USER_LOGGED_IN_COOKIE_NAME = "userLoggedIn";
const REGISTRATION_PATH = "/register";
const LOGIN_PATH = "/login";
const LOGOUT_PATH = "/logout";
const PROFILE_PATH = "/api/v1/profile";

class TrackrUserService {
  /**
   * This check if we have a user logged by looking at local storage. No sensitive information is stored there.
   * Note: JWT is **not** accessible from JavaScript and not sored in localStorage to prevent XSS attack.
   *
   * @returns {boolean}
   */
  static isAuthenticated() {
    return Cookies.get(USER_LOGGED_IN_COOKIE_NAME) === "true";
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
    return axios.post(LOGOUT_PATH);
  };

  /**
   * Get user profile.
   *
   * @returns {Promise<AxiosResponse<any>>} Promise
   */
  getProfile = () => {
    return axios.get(PROFILE_PATH);
  };

  /**
   * Update user profile.
   *
   * @param values Form values
   * @returns {Promise<AxiosResponse<any>>} Promise
   */
  updateProfile = (values) => {
    return axios.put(PROFILE_PATH, values);
  };
}

export default TrackrUserService;
