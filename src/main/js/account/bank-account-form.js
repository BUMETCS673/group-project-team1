/**
 * Bank account form.
 *
 * @author Jean Dorancy
 */

import React from "react";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { Button, FormGroup, FormLabel } from "react-bootstrap";
import * as Yup from "yup";

// Account types constants.
const CHECKING = "CHECKING";
const SAVING = "SAVING";
const RETIREMENT = "RETIREMENT";
const OTHER = "OTHER";

const BankAccountForm = (props) => {
  return (
    <Formik
      initialValues={{
        accountType: props.bankAccount.accountType || CHECKING,
        accountDescription: props.bankAccount.accountDescription || "",
        balance: props.bankAccount.balance || 0,
      }}
      validationSchema={Yup.object({
        accountType: Yup.string()
          .max(20, "Must be 20 characters or less")
          .required("Required"),
        accountDescription: Yup.string()
          .max(255, "Must be 255 characters or less")
          .required("Required"),
        balance: Yup.number().moreThan(0).required("Required"),
      })}
      onSubmit={(values, actions) => {
        props.handleBankAccountFormSubmit(values);
        actions.setSubmitting(false);
        actions.resetForm();
      }}
    >
      <Form>
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="accountType" className="form-label col-md-5">
            Select Type
          </FormLabel>
          <Field
            id="accountType"
            as="select"
            name="accountType"
            className="form-control col-md-5"
          >
            <option value={CHECKING}>{CHECKING}</option>
            <option value={SAVING}>{SAVING}</option>
            <option value={RETIREMENT}>{RETIREMENT}</option>
            <option value={OTHER}>{OTHER}</option>
          </Field>
          <ErrorMessage name="accountType" component="div" />
        </FormGroup>
        <FormGroup className="mt-md-2">
          <FormLabel
            htmlFor="accountDescription"
            className="form-label col-md-5"
          >
            Description
          </FormLabel>
          <Field
            id="accountDescription"
            type="text"
            name="accountDescription"
            className="form-control col-md-5"
          />
          <ErrorMessage name="accountDescription" component="div" />
        </FormGroup>
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="balance" className="form-label col-md-5">
            Balance
          </FormLabel>
          <Field
            id="balance"
            type="text"
            name="balance"
            className="form-control col-md-5"
          />
          <ErrorMessage name="balance" component="div" />
        </FormGroup>
        <Button type="submit" variant="primary" className="mt-md-4 float-end">
          Save Changes
        </Button>
      </Form>
    </Formik>
  );
};

export default BankAccountForm;
