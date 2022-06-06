/**
 * Transaction form.
 *
 * @author Xiaobing Hou
 */

import React from "react";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { Button, FormGroup, FormLabel } from "react-bootstrap";
import * as Yup from "yup";

const TransactionForm = (props) => {
  const date = new Date();
  return (
    <Formik
      initialValues={{
        bankAccountId: props.bankAccountId,
        money: props.isAddTransaction ? 0 : props.selectedTransaction.money,
        counterparty: props.isAddTransaction
          ? ""
          : props.selectedTransaction.counterparty,
        time: props.isAddTransaction
          ? date.getMonth() +
            1 +
            "/" +
            date.getDate() +
            "/" +
            date.getFullYear()
          : props.selectedTransaction.time,
        transactionDescription: props.isAddTransaction
          ? ""
          : props.selectedTransaction.transactionDescription,
      }}
      validationSchema={Yup.object({
        money: Yup.number().required("Required").min(!0, "Cannot be zero"),
        counterparty: Yup.string()
          .max(100, "Must be 100 characters or less")
          .required("Required"),
        transactionDescription: Yup.string().max(
          100,
          "Must be 100 characters or less"
        ),
      })}
      onSubmit={(values, actions) => {
        if (props.isAddTransaction) {
          props.handleAddTransactions(values);
        } else {
          props.handleEditTransactions(props.selectedTransaction.id, values);
        }
        actions.setSubmitting(false);
        actions.resetForm();
      }}
    >
      <Form>
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="money" className="form-label col-md-5">
            Amount
          </FormLabel>
          <Field type="text" name="money" className="form-control col-md-5" />
          <ErrorMessage name="money" component="div" />
        </FormGroup>
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="counterparty" className="form-label col-md-5">
            Counterparty
          </FormLabel>
          <Field
            type="text"
            name="counterparty"
            className="form-control col-md-5"
          />
          <ErrorMessage name="counterparty" component="div" />
        </FormGroup>
        <FormGroup className="mt-md-2">
          <FormLabel
            htmlFor="transactionDescription"
            className="form-label col-md-5"
          >
            Description
          </FormLabel>
          <Field
            type="text"
            name="transactionDescription"
            className="form-control col-md-5"
          />
          <ErrorMessage name="transactionDescription" component="div" />
        </FormGroup>
        <Button type="submit" variant="primary" className="mt-md-4 float-end">
          Save Changes
        </Button>
      </Form>
    </Formik>
  );
};

export default TransactionForm;
