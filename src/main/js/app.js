import React from "react";
import { createRoot } from "react-dom/client";
import { Routes } from "react-router";
import { BrowserRouter, Route } from "react-router-dom";
import HomeContainer from "./home-container";
import Layout from "./layout";
import Login from "./login";
import "bootstrap/dist/css/bootstrap.css";

class App extends React.Component {
  render() {
    return (
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<HomeContainer />} />
            <Route path="login" element={<Login />} />
          </Route>
        </Routes>
      </BrowserRouter>
    );
  }
}

createRoot(document.getElementById("react")).render(<App />);
