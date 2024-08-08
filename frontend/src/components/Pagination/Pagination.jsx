import React from "react";
import { ReactComponent as ArrowLeftIcon } from "../../assets/icons/arrow-left.svg";
import { ReactComponent as ArrowRightIcon } from "../../assets/icons/arrow-right.svg";
import styles from "./Pagination.module.scss";

export const Pagination = ({
  currentPage,
  totalItems,
  itemsPerPage,
  onPageChange,
  onItemsPerPageChange,
}) => {
  const totalPages = Math.ceil(totalItems / itemsPerPage);
  const startItem = (currentPage - 1) * itemsPerPage + 1;
  const endItem = Math.min(currentPage * itemsPerPage, totalItems);

  const handleItemsPerPageChange = (event) => {
    onItemsPerPageChange(Number(event.target.value));
  };
  return (
    <div style={{ display: "flex", alignItems: "center" }}>
      <label htmlFor="itemsPerPage">Rows per page:</label>

      <select
        className={styles.select}
        name="itemsPerPage"
        id="itemsPerPage"
        value={itemsPerPage}
        onChange={handleItemsPerPageChange}
      >
        <option value="2">2</option>
        <option value="4">4</option>
        <option value="8">8</option>
      </select>

      <span style={{ marginRight: "10px" }}>
        {startItem}-{endItem} of {totalItems}
      </span>
      <button
        className={styles.arrow_icon}
        onClick={() => onPageChange(currentPage - 1)}
        disabled={currentPage === 1}
        style={{ marginRight: "10px" }}
      >
        <ArrowLeftIcon />
      </button>
      <button
        className={styles.arrow_icon}
        onClick={() => onPageChange(currentPage + 1)}
        disabled={currentPage === totalPages}
        style={{ marginRight: "10px" }}
      >
        <ArrowRightIcon />
      </button>
    </div>
  );
};
