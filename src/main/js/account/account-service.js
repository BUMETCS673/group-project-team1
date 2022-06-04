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
   * @author Xiaobing Hou
   * @date 06/03/2022
   */
  findAllBankAccount = () => {
    return axios.get(PATH);
  };
}

export default AccountService;
