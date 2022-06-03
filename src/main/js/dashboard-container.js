/**
 * Dashboard container to manage state and logic for the user dashboard page.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Dashboard from "./dashboard";
import AccountService from "./account/account-service";
import AccountTable from "./account/account-table";
import {createRoot} from "react-dom/client";
/**
 * Dashboard container to manage state and logic for the user dashboard page.
 *
 * @author Xiaobing Hou
 */
const DashboardContainer = (props) => {
    let service = new AccountService();

    service.findAllBankAccount()
        .then(function (response) {
            createRoot(document.getElementById("dashboard")).render(<AccountTable
                bankAccount={response.data.additionalData}/>);
        })
        .catch(function (error) {
            props.setAlert({
                show: true,
                variant: "danger",
                message: `${error.response.data.message}`,
            });
            setTimeout(() => props.setAlert({show: false}), 2000);
        });
    return <Dashboard/>;
};

export default DashboardContainer;
