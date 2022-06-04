/**
 * Bank account form.
 *
 * @author Jean Dorancy
 */

import React from "react";
import {ErrorMessage, Field, Form, Formik} from "formik";
import {Button, FormGroup, FormLabel, Row} from "react-bootstrap";
import * as Yup from "yup";

const BankAccountForm = (props) => {
  return (
      <Formik
          initialValues={{
            type: props.bankAccount.accountType,
            description: props.bankAccount.accountDescription,
            balance: props.bankAccount.balance
          }}
          validationSchema={Yup.object({
            // firstName: Yup.string()
            //   .max(100, "Must be 100 characters or less")
            //   .required("Required"),
            // lastName: Yup.string()
            //   .max(100, "Must be 100 characters or less")
            //   .required("Required"),
            // username: Yup.string()
            //   .max(50, "Must be 50 characters or less")
            //   .required("Required"),
            // email: Yup.string()
            //   .max(50, "Must be 50 characters or less")
            //   .email("Invalid email address")
            //   .required("Required"),
            // password: Yup.string()
            //   .min(6, "Must be at least 6 characters")
            //   .max(200, "Must be less than 200 characters")
            //   .required("Required"),
          })}
          onSubmit={(values, actions) => {
            // props.handleSignUpFormSubmit(values);
            console.log("submitting");
            actions.setSubmitting(false);
            actions.resetForm();
          }}
      >
        <Form className="form-control">
          <FormGroup className="mt-md-2">
            <FormLabel htmlFor="type" className="form-label col-md-5">
              Type
            </FormLabel>
            <Field
                as="select"
                name="type"
                className="form-control col-md-5"
            >
              <option value="CHECKING">CHECKING</option>
              <option value="SAVING">SAVING</option>
              <option value="RETIREMENT">RETIREMENT</option>
              <option value="OTHER">OTHER</option>
            </Field>
            <ErrorMessage name="type" component="div" />
          </FormGroup>
          <FormGroup className="mt-md-2">
            <FormLabel htmlFor="description" className="form-label col-md-5">
              Description
            </FormLabel>
            <Field
                type="text"
                name="description"
                className="form-control col-md-5"
            />
            <ErrorMessage name="description" component="div" />
          </FormGroup>
          <FormGroup className="mt-md-2">
            <FormLabel htmlFor="balance" className="form-label col-md-5">
              Balance
            </FormLabel>
            <Field
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
