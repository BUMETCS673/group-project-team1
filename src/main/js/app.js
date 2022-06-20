/**
 * Frontend application entrypoint which renders inside the HTML element "react" from index.html.
 * Define routes using React Router for other pages.
 *
 * @author Jean Dorancy
 */

import React, { useState } from "react";
import { createRoot } from "react-dom/client";
import { BrowserRouter, Route } from "react-router-dom";
import { Routes } from "react-router";
import TrackrUserService from "./user/trackr-user-service";
import ProtectedRoute from "./auth/protected-route";
import Layout from "./layout";
import HomeContainer from "./home-container";
import LoginContainer from "./auth/login-container";
import DashboardContainer from "./dashboard-container";
import AccountContainer from "./account/account-container";
import ProfileContainer from "./user/profile-container";
import "bootstrap/dist/css/bootstrap.css";

const App = () => {
  const [alert, setAlert] = useState({ show: false });

  return (
    <BrowserRouter>
      <Routes>
        <Route
          path="/"
          element={
            <Layout
              setAlert={setAlert}
              alert={alert}
              isAuthenticated={TrackrUserService.isAuthenticated()}
            />
          }
        >
          {TrackrUserService.isAuthenticated() ? (
            <Route index element={<DashboardContainer setAlert={setAlert} />} />
          ) : (
            <Route index element={<HomeContainer setAlert={setAlert} />} />
          )}

          <Route
            path="login"
            element={<LoginContainer setAlert={setAlert} />}
          />
          <Route
            element={
              <ProtectedRoute
                isAuthenticated={TrackrUserService.isAuthenticated()}
              />
            }
          >
            <Route
              path="dashboard"
              element={<DashboardContainer setAlert={setAlert} />}
            />
            <Route
              path="profile"
              element={<ProfileContainer setAlert={setAlert} />}
            />
            <Route
              path="accounts"
              element={<AccountContainer setAlert={setAlert} />}
            />
          </Route>
        </Route>
      </Routes>
    </BrowserRouter>
  );
};

createRoot(document.getElementById("react")).render(<App />);
