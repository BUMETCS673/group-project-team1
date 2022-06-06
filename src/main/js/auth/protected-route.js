/**
 * Protected route React component.
 *
 * @author Jean Dorancy
 */
import React from "react";
import { Navigate, Outlet } from "react-router";

const ProtectedRoute = ({
  isAuthenticated,
  redirectPath = "/login",
  children,
}) => {
  if (!isAuthenticated) {
    return <Navigate to={redirectPath} replace />;
  }

  return children ? children : <Outlet />;
};

export default ProtectedRoute;
