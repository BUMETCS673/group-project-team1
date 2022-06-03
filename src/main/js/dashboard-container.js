/**
 * Dashboard container to manage state and logic for the user dashboard page.
 *
 * @author Jean Dorancy
 */

import React from "react";
import Dashboard from "./dashboard";
import {createRoot} from "react-dom/client";
import AccountService from "./account/account-service";
import Account from "./account/account";

const DashboardContainer = (props) => {
    let service = new AccountService();

    service.findAllBankAccount()
        .then(function (response) {
            createRoot(document.getElementById("dashboardAccounts"))
                .render(<Account bankAccount={response.data.additionalData}/>);
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
