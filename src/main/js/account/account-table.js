
import React from "react";
import {Container, Table} from "react-bootstrap";

/**
 * The purpose of this method is to generate teh bank account table
 *
 * @author Xiaobing Hou
 * @date 06/03/2022
 */
const AccountTable = (props) => {
    let total = 0;
    for (let i = 0; i < props.bankAccount.length; i++) {
        total = total + props.bankAccount[i].balance;
    }
    total = total.toFixed(2);
    return (
        <Container>
            <Table striped bordered hover>
                <thead>
                <tr>
                    <th>#</th>
                    <th>Type</th>
                    <th>status</th>
                    <th>Description</th>
                    <th>Balance</th>
                </tr>
                </thead>

                <tbody>

                {Array.from({length: props.bankAccount.length}).map((_, index) => (
                    <tr key={index}>
                        <td>{index}</td>
                        <td>{props.bankAccount[index].accountType}</td>
                        <td>{props.bankAccount[index].status}</td>
                        <td>{props.bankAccount[index].accountDescription}</td>
                        <td>{props.bankAccount[index].balance}</td>
                    </tr>
                ))}

                <tr>
                    <th colSpan={4}>Total Cash</th>
                    <th>{total}</th>
                </tr>
                </tbody>
            </Table>
        </Container>
    );
};
export default AccountTable;
