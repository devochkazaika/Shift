/* eslint-disable  */
import { Form, Formik } from "formik";
import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

import { initialStoryValues } from "../utils/constants/initialValues";
import CommonForm from "../components/Stories/CommonForm";
import Loader from "../components/ui/Loader/index";
import StoryPanel from "../components/Stories/ExistStory/StoryPanel";
import Button from "../components/ui/Button";
import { ReactComponent as ArrowIcon } from "../assets/icons/arrow-up.svg";
import { Pagination } from "../components/Pagination/Pagination";

const StoriesList = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [storiesArray, setStoriesArray] = useState([]);

  const [bankId, setBankId] = useState("absolutbank");
  const [platform, setPlatform] = useState("ALL PLATFORMS");

  const [currentPage, setCurrentPage] = React.useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(8);

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };
  const fetchData = async (bankId, platform) => {
    try {
      const response = await axios.get(
        "/stories/bank/info/getJson/?bankId=" +
          bankId +
          "&platform=" +
          platform,
        {
          headers: {},
          responseType: "json",
        },
      );
      return response.data;
    } catch (error) {
      console.error("Error fetching data:", error);
      return null;
    }
  };

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await fetchData(bankId, platform);
        if (data == null) {
          setStoriesArray([]);
        } else {
          setStoriesArray(data);
          setCurrentPage(1);
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchDataAsync();
  }, [bankId, platform]);

  const totalItems = storiesArray.length;
  const startIndex = (currentPage - 1) * itemsPerPage;

  const currentItems = storiesArray.slice(
    startIndex,
    startIndex + itemsPerPage,
  );
  const handleItemsPerPageChange = (newItemsPerPage) => {
    setItemsPerPage(newItemsPerPage);
    setCurrentPage(1);
  };
  return (
    <>
      <h1>Stories</h1>

      <div className="stories">
        <Button
          text="Добавить"
          type="button"
          color="green"
          icon={<ArrowIcon width="12px" height="12px" />}
          handleOnClick={() => navigate("/addStories")}
        />
        <Formik enableReinitialize initialValues={initialStoryValues}>
          {(props) => (
            <Form>
              <CommonForm
                setBankId={setBankId}
                setPlatform={setPlatform}
                {...props}
              />
            </Form>
          )}
        </Formik>
      </div>
      {storiesArray.length > 0 ? (
        <StoryPanel storyArray={currentItems} platform={platform} />
      ) : (
        <h2>Пока нет историй</h2>
      )}
      <div className="stories">
        <Pagination
          currentPage={currentPage}
          totalItems={totalItems}
          itemsPerPage={itemsPerPage}
          onPageChange={handlePageChange}
          onItemsPerPageChange={handleItemsPerPageChange}
        />
      </div>

      {loading && <Loader />}
    </>
  );
};

export default StoriesList;
