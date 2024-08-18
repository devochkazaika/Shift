import React, { useEffect, useState } from "react";

import { Pagination } from "../components/Pagination/Pagination";
import {  getAllHistory } from "../api/stories";
import styles from './HistoryList.module.scss'
const History = () => {
  const [historyArray, setHistoryArray] = useState([]);
  const [currentPage, setCurrentPage] = React.useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(8);

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await getAllHistory();
        if (data == null) {
          setHistoryArray([]);
        } else {
          setHistoryArray(data);
          setCurrentPage(1);
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchDataAsync();
  }, []);

  const totalItems = historyArray.length;
  const startIndex = (currentPage - 1) * itemsPerPage;

  const currentItems = historyArray.slice(
    startIndex,
    startIndex + itemsPerPage,
  );
  const handleItemsPerPageChange = (newItemsPerPage) => {
    setItemsPerPage(newItemsPerPage);
    setCurrentPage(1);
  };

  return (
    <>
      <h1>History</h1>
      <div className="stories">
        <h2>Список операций для сторис</h2>
        <table className={styles.operations_table}>
          <thead>
            <tr>
              <th>Дата</th>
              <th>Время</th>
              <th>Тип операции</th>
              <th>Статус операции</th>
              <th>Имя пользователя</th>
            </tr>
          </thead>
          <tbody>
            {historyArray.length > 0 ? (
              currentItems.map((operation) => (
                <tr key={operation.id}>
                  <td>{`${operation.day[0]}-${operation.day[1]}-${operation.day[2]}`}</td>
                  <td>{`${operation.time[0]}:${operation.time[1]}:${operation.time[2]}`}</td>
                  <td>{operation.operationType}</td>
                  <td
                    style={{
                      color:
                        operation.status === "SUCCESSFUL" ? "green" : "black",
                    }}
                  >
                    {operation.status}
                  </td>
                  <td>{operation.userName}</td>
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan="5" className={styles.noOperations}>
                  Пока нет операций
                </td>
              </tr>
            )}
          </tbody>
        </table>
        <Pagination
          currentPage={currentPage}
          totalItems={totalItems}
          itemsPerPage={itemsPerPage}
          onPageChange={handlePageChange}
          onItemsPerPageChange={handleItemsPerPageChange}
        />
      </div>
    </>
  );
};

export default History;
