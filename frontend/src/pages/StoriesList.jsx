/* eslint-disable  */
import { Form, Formik } from "formik";
import React, {useEffect, useState } from "react";

import { initialStoryValues } from "../utils/constants/initialValues";
import CommonForm from "../components/Stories/CommonForm";
import Loader from "../components/ui/Loader/index";
import StoryPanel from "../components/Stories/ExistStory/StoryPanel";
import { Pagination } from "../components/Pagination/Pagination";
import { getStories } from "../api/stories";

const StoriesList = ({children}) => {
  const [loading, setLoading] = useState(false);
  const [storiesArray, setStoriesArray] = useState([]);

  const [bankId, setBankId] = useState("absolutbank");
  const [platform, setPlatform] = useState("ALL PLATFORMS");

  const [currentPage, setCurrentPage] = React.useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(8);

  const handlePageChange = (newPage) => {
    setCurrentPage(newPage);
  };

  useEffect(() => {
    const fetchDataAsync = async () => {
      try {
        const data = await getStories(bankId, platform);
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
        {children}
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
