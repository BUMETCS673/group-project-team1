/**
 * Account service to abstract the logic to interact with the bank account API.
 *
 * @author Xiaobing Hou
 * @date 06/03/2022
 */

import axios from "axios";

const PATH = "/api/v1/bank-accounts";

class AccountService {

    findAllBankAccount = () => {
        const token = sessionStorage.getItem('trackrToken')

        axios.interceptors.request.use(function (config) {
            config.headers.Authorization = "Bearer " + token;
            return config;
        })

        return axios.get(`${PATH}`);

    };
}

export default AccountService;
