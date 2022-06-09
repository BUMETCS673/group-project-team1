/**
 * Account service to abstract the logic to interact with the transaction API.
 *
 * @author Xiaobing Hou
 * @date 06/04/2022
 */

import axios from "axios";

const PATH = "/api/v1/transaction";
const FIND_ALL_PATH = "findAll";
const DEL_PATH = "delete";
const EDIT_PATH = "modify";

class TransactionService {
  /**
   * To get all transaction .
   *
   * @param bankAccountId
   * @returns {Promise<AxiosResponse<any>>} Promise
   * @author Xiaobing Hou
   * @date 06/03/2022
   */
  findAllTransactionsByBankId = (bankAccountId) => {
    return axios.get(`${PATH}/${FIND_ALL_PATH}/${bankAccountId}`);
  };

  /**
   * To del transaction .
   *
   * @param transactionId
   * @param bankAccountId
   * @returns {Promise<AxiosResponse<any>>} Promise
   * @author Xiaobing Hou
   * @date 06/03/2022
   */
  deleteTranByTranIdAndBankId = (transactionId, bankAccountId) => {
    return axios.delete(
      `${PATH}/${transactionId}/${bankAccountId}`
    );
  };

  /**
   * To edit transaction .
   *
   * @param transactionId
   * @param values
   * @returns {Promise<AxiosResponse<any>>} Promise
   * @author Xiaobing Hou
   * @date 06/03/2022
   */
  editTranByTranIdAndBankId = (transactionId, values) => {
    return axios.put(`${PATH}/${transactionId}`, values);
  };

  /**
   * To add transaction .
   * @param values
   * @returns {Promise<AxiosResponse<any>>} Promise
   * @author Xiaobing Hou
   * @date 06/03/2022
   */
  addTransaction = (values) => {
    return axios.post(PATH, values);
  };
}

export default TransactionService;
