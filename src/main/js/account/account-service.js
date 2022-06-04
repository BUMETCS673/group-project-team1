/**
 * Account service to abstract the logic to interact with the bank account API.
 *
 * @author Xiaobing Hou
 * @date 06/03/2022
 */

import axios from "axios";

const PATH = "/api/v1/bank-accounts";

class AccountService {
  /**
   * To get all bank accounts .
   *
   * @returns {Promise<AxiosResponse<any>>} Promise
   * @author Xiaobing Hou
   * @date 06/03/2022
   */
  findAllBankAccount = () => {
    return axios.get(PATH);
  };

  /**
   * Find bank account by id
   * @param id Bank account id
   * @returns {Promise<AxiosResponse<any>>} Promise
   */
  findBankAccountById = (id) => {
    return axios.get(`${PATH}/${id}`);
  };
}


export default AccountService;
