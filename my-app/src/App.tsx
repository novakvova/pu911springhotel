import * as React from 'react';
import { Routes, Route } from 'react-router-dom';
//import logo from './logo.svg';
//import './App.css';
import Home from "./components/Home";
import DefaultLayout from "./components/containers/DefaultLayout";

function App() {
  return (
    <Routes>
      <Route path="/" element={<DefaultLayout/>}>
          <Route index element={<Home/>} />
        </Route>
    </Routes>
  );
}

export default App;
