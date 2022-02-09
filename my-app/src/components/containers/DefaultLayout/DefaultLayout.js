import * as React from 'react';
import { Outlet } from "react-router";
import Header from './Header';
import Breadcrumbs from '../../common/Breadcrumbs';


const DefaultLayout = () => {
    return (
      <>
        <Header />

        <Breadcrumbs/>

        <div>
          <Outlet />
        </div>
      </>
    );
}
export default DefaultLayout;