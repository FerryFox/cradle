import { Route, Navigate } from 'react-router-dom';
import React from "react";

function PrivateRoute({ path, element, isLoggedIn })
{
    return isLoggedIn
        ? <Route path={path} element={element}/>
        : <Navigate to="/"></Navigate>
}

export default PrivateRoute;