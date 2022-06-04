import React, { useEffect, useState } from "react";
import Container from "react-bootstrap/Container";
import { Table } from "react-bootstrap";

/**
 * The purpose of this method is to generate teh bank account table
 *
 * @author Xiaobing Hou
 * @date 06/03/2022
 */
const Account = (props) => {
  return (
    <Container>
      {props.bankAccounts.map((item) => (
        <Table
          key={item.id}
          style={{
            margin: "10px 0px 10px 0px",
          }}
        >
          <tbody>
            <tr>
              <td
                style={{
                  width: "70%",
                }}
              >
                {item.accountType}
                <br />
                <span>
                  <font color={"gray"}>{item.accountDescription}</font>
                </span>
              </td>
              <td>{item.balance}</td>
            </tr>
          </tbody>
        </Table>
      ))}

      <Table
        style={{
          margin: "20px 0px 50px 0px",
        }}
      >
        <tbody>
          <tr>
            <td
              style={{
                width: "70%",
              }}
            >
              <h5>Total Cash</h5>
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
  if (props.bankAccounts) {
    for (let i = 0; i < props.bankAccounts.length; i++) {
      total = total + props.bankAccounts[i].balance;
    }
  }
  total = total.toFixed(2);
  return total;
}

export default Account;
