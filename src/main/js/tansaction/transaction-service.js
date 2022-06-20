/**
 * Account service to abstract the logic to interact with the transaction API.
 *
 * @author Xiaobing Hou
 * @date 06/04/2022
 */

import axios from "axios";

const PATH = "/api/v1/transactions";

class TransactionService {
  /**
   * To get all transactions.
   *
   * @returns {Promise<AxiosResponse<any>>} Promise
   * @author Xiaobing Hou
   * @date 06/03/2022
   */
  findAllTransactions = () => {
    return axios.get(`${PATH}`);
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
    return axios.delete(`${PATH}/${bankAccountId}/${transactionId}`);
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
