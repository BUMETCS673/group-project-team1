/**
 * The signup form built using Formik for form element and Yup for validation.
 *
 * @author Jean Dorancy
 */

import React from "react";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { Button, FormGroup, FormLabel } from "react-bootstrap";
import * as Yup from "yup";

const SignUpForm = (props) => {
  return (
    <Formik
      enableReinitialize={true}
      initialValues={{
        firstName: "",
        lastName: "",
        email: "",
        username: "",
        password: "",
      }}
      validationSchema={Yup.object({
        firstName: Yup.string()
          .max(100, "Must be 100 characters or less")
          .required("Required"),
        lastName: Yup.string()
          .max(100, "Must be 100 characters or less")
          .required("Required"),
        username: Yup.string()
          .max(50, "Must be 50 characters or less")
          .required("Required"),
        email: Yup.string()
          .max(50, "Must be 50 characters or less")
          .email("Invalid email address")
          .required("Required"),
        password: Yup.string()
          .min(6, "Must be at least 6 characters")
          .max(200, "Must be less than 200 characters")
          .required("Required"),
      })}
      onSubmit={(values, actions) => {
        props.handleUserFormSubmit(values);
        actions.setSubmitting(false);
        actions.resetForm();
      }}
    >
      <Form className="form-control">
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="firstName" className="form-label col-md-5">
            First name
          </FormLabel>
          <Field
            id="firstName"
            type="text"
            name="firstName"
            className="form-control col-md-5"
          />
          <ErrorMessage name="firstName" component="div" />
        </FormGroup>
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="lastName" className="form-label col-md-5">
            Last name
          </FormLabel>
          <Field
            id="lastName"
            type="text"
            name="lastName"
            className="form-control col-md-5"
          />
          <ErrorMessage name="lastName" component="div" />
        </FormGroup>
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="username" className="form-label col-md-5">
            Username
          </FormLabel>
          <Field
            id="username"
            type="text"
            name="username"
            className="form-control col-md-5"
          />
          <ErrorMessage name="username" component="div" />
        </FormGroup>
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="email" className="form-label col-md-5">
            Email address
          </FormLabel>
          <Field
            id="email"
            type="email"
            name="email"
            className="form-control col-md-5"
          />
          <ErrorMessage name="email" component="div" />
        </FormGroup>

        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="password" className="form-label col-md-5">
            Password
          </FormLabel>
          <Field
            id="password"
            type="password"
            name="password"
            className="form-control col-md-5"
          />
          <ErrorMessage name="password" component="div" />
        </FormGroup>

        <Button type="submit" className="form-control btn-primary mt-md-2">
          <h5>Sign Up!</h5>
        </Button>
      </Form>
    </Formik>
  );
};

export default SignUpForm;
