import React, { useEffect, useState } from "react";
import axios from "axios";
import keycloak from "../Security/Keycloak";

const GetUsers = () => {
    const [users, setUsers] = useState([]);

    const fetchUsers = async () => {
        try {

            // Get the Keycloak token
            const token = keycloak.token;

            // Make a request to the Keycloak Admin API to fetch users
            const response = await axios.get(
                'http://localhost:8080/admin/realms/content-maker/groups?search=Users',
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );

            // Set users to state
            setUsers(response.data);
            console.log("AAAAA" + response.data);
        } catch (error) {
            console.error('Failed to fetch users', error);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    return (
        <div>
            <h1>User List</h1>
            <ul>
                {users.map(user => (
                    <li key={user.id}>{user.username}</li>
                ))}
            </ul>
        </div>
    );
}

export default GetUsers;