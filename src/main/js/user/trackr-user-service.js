/**
 * User service to abstract the logic to interact with the user API.
 *
 * @author Jean Dorancy
 */

import axios from "axios";

const REGISTRATION_PATH = "/register";
const LOGIN_PATH = "/login";

class TrackrUserService {
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
   * Get JWT token by sending user login.
   *
   * @param login User login credentials
   * @returns {Promise<AxiosResponse<any>>} Promise
   */
  getToken = (login) => {
    return axios.post(LOGIN_PATH, login);
  };
}

export default TrackrUserService;
