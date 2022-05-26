/**
 * User service to abstract the logic to interact with the user API.
 *
 * @author Jean Dorancy
 */

import axios from "axios";

const PATH = "/api/v1/users";

class TrackrUserService {
  /**
   * Create a user.
   *
   * @param user User object with all the attributes.
   * @returns {Promise<AxiosResponse<any>>} Promise
   */
  create = (user) => {
    return axios.post(PATH, user);
  };

  /**
   * Get JWT token by sending user login.
   *
   * @param login User login credentials
   * @returns {Promise<AxiosResponse<any>>} Promise
   */
  getToken = (login) => {
    return axios.post(`${PATH}/retrieve-token`, login);
  };
}

export default TrackrUserService;
