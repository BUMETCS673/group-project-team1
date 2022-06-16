/**
 * The login form built using Formik for form element.
 *
 * @author Jean Dorancy
 */

import React from "react";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { Button, FormGroup, FormLabel } from "react-bootstrap";
import * as Yup from "yup";

const LoginForm = (props) => {
  return (
    <Formik
      initialValues={{
        username: "",
        password: "",
      }}
      validationSchema={Yup.object({
        username: Yup.string().required("Required"),
        password: Yup.string().required("Required"),
      })}
      onSubmit={(values, actions) => {
        props.handleLoginSubmit(values);
        actions.setSubmitting(false);
        actions.resetForm();
      }}
    >
      <Form className="form-control">
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
          <h5>Login</h5>
        </Button>
      </Form>
    </Formik>
  );
};

export default LoginForm;
