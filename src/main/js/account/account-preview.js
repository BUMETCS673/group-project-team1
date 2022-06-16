import React from "react";
import Container from "react-bootstrap/Container";
import { Col, ListGroup, Row } from "react-bootstrap";

/**
 * The purpose of this method is to get the total
 *
 * @author Xiaobing Hou
 * @date 06/03/2022
 */
const getTotal = (bankAccounts) => {
  let total = 0;
  if (bankAccounts) {
    for (let i = 0; i < bankAccounts.length; i++) {
      total = total + bankAccounts[i].balance;
    }
  }
  total = total.toFixed(2);
  return total;
};

/**
 * The purpose of this method is to generate the bank account preview table.
 *
 * @author Xiaobing Hou
 * @date 06/03/2022
 */
const AccountPreview = (props) => {
  return (
    <Container className="mt-md-4">
      <ListGroup defaultActiveKey="#link1">
        {props.bankAccounts.map((item) => (
          <ListGroup.Item key={item.id}>
            <Row>
              <Col className="text-start" md={8}>
                {item.accountType}
              </Col>

              <Col className="text-end" md={4}>
                ${item.balance}
              </Col>
            </Row>

            <Row>
              <Col className="text-start" md={10}>
                <font color={"gray"}>{item.accountDescription}</font>
              </Col>
            </Row>
          </ListGroup.Item>
        ))}
        <ListGroup.Item>
          <Row className="mt-md-4">
            <Col className="text-start" md={5}>
              <h5>Total Cash</h5>
            </Col>
            <Col className="text-end" md={7}>
              ${getTotal(props.bankAccounts)}
            </Col>
          </Row>
        </ListGroup.Item>
      </ListGroup>
    </Container>
  );
};

export default AccountPreview;
