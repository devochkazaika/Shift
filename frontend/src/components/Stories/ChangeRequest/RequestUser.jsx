import React, { useEffect, useState } from 'react';
import { getRequestsUser } from '../../../api/history';
import RequestPanel from './RequestPanel';

const RequestUser = () => {
    const [historyArray, setHistoryArray] = useState([]);
    
    useEffect(() => {
        const fetchDataAsync = async () => {
            try {
                const requests = await getRequestsUser();
                setHistoryArray(requests);
            } catch (error) {
              console.error("Error fetching data:", error);
            }
          };
      
        fetchDataAsync();
    }, []);

    return (
        <>
            {historyArray.length > 0 ? (
                historyArray.map((value, index) => (
                    <div key={index}>
                        <RequestPanel data={value} />
                    </div>
                ))
            ) : (
                'Loading...'
            )}
        </>
    )
}

export default RequestUser;