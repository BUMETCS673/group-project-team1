/**
 * The signup form built using Formik for form element and Yup for validation.
 *
 * @author Xiaobing Hou
 */

import React from "react";
import { ErrorMessage, Field, Form, Formik } from "formik";
import { Button, Col, FormGroup, FormLabel, Row } from "react-bootstrap";

const ProfileForm = (props) => {
  return (
    <Formik
      enableReinitialize={true}
      initialValues={{
        firstName: props.user.firstName ? props.user.firstName : "",
        lastName: props.user.lastName ? props.user.lastName : "",
        email: props.user.email ? props.user.email : "",
        password: "",
        newPassword: "",
      }}
      validationSchema={
        props.isChangePwd
          ? props.validationSchemaChange
          : props.validationSchema
      }
      onSubmit={(values, actions) => {
        props.handleUserFormSubmit(values);
        props.setIsChangePwd(false);
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
            type="text"
            name="lastName"
            className="form-control col-md-5"
          />
          <ErrorMessage name="lastName" component="div" />
        </FormGroup>
        <FormGroup className="mt-md-2">
          <FormLabel htmlFor="email" className="form-label col-md-5">
            Email address
          </FormLabel>
          <Field type="email" name="email" className="form-control col-md-5" />
          <ErrorMessage name="email" component="div" />
        </FormGroup>

        {!props.isChangePwd && (
          <Button variant="link" onClick={() => props.setIsChangePwd(true)}>
            Change Password
          </Button>
        )}
        {props.isChangePwd && (
          <Row>
            <Col md={9}>
              <FormGroup className="mt-md-2">
                <FormLabel htmlFor="password" className="form-label col-md-9">
                  Old Password
                </FormLabel>
                <Field
                  type="password"
                  name="password"
                  className="form-control col-md-5"
                />
                <ErrorMessage name="password" component="div" />

                <FormLabel
                  htmlFor="newPassword"
                  className="form-label col-md-9"
                >
                  New Password
                </FormLabel>
                <Field
                  type="password"
                  name="newPassword"
                  className="form-control col-md-5"
                />
                <ErrorMessage name="newPassword" component="div" />
              </FormGroup>
            </Col>
            <Col md={3}>
              <Button
                variant="link"
                onClick={() => props.setIsChangePwd(false)}
              >
                Cancel Change
              </Button>
            </Col>
          </Row>
        )}
        <Button type="submit" className="form-control btn-primary mt-md-2">
          <h5>Save</h5>
        </Button>
      </Form>
    </Formik>
  );
};

export default ProfileForm;
