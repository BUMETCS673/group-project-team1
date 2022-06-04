import React from "react";
import Container from "react-bootstrap/Container";
import {Table} from "react-bootstrap";

/**
 * The purpose of this method is to generate teh bank account table
 *
 * @author Xiaobing Hou
 * @date 06/03/2022
 */
const Account = (props) => {

    return (
        <Container>

            {Array.from({length: props.bankAccount.length}).map((_, index) => (

                <Table key={index} style={{
                    margin: "10px 0px 10px 0px",
                }}
                >
                    <tbody>
                    <tr>
                        <td style={{
                            width: "70%"
                        }}>{props.bankAccount[index].accountType}
                            <br/>
                            <span><font color={"gray"}>{props.bankAccount[index].accountDescription}</font></span>
                        </td>
                        <td>{props.bankAccount[index].balance}</td>
                    </tr>
                    </tbody>
                </Table>

            ))}

            <Table style={{
                margin: "20px 0px 50px 0px"
            }}
            >
                <tbody>
                <tr>
                    <td style={{
                        width: "70%"
                    }}><h5>Total Cash</h5>
                    </td>
                    <td>{getTotal(props)}</td>
                </tr>
                </tbody>
            </Table>

        </Container>
    );
};

/**
 * The purpose of this method is to get the total
 *
 * @author Xiaobing Hou
 * @date 06/03/2022
 */
function getTotal(props) {
    let total = 0;
    for (let i = 0; i < props.bankAccount.length; i++) {
        total = total + props.bankAccount[i].balance;
    }
    total = total.toFixed(2);
    return total;

}

export default Account;
