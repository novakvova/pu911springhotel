import * as React from 'react';
import { Outlet } from "react-router";
import Header from './Header';

const DefaultLayout : React.FC = () => {
    return (
        <>
            <Header/>
            <div>
                <Outlet/>
            </div>

        </>
    );
}
export default DefaultLayout;