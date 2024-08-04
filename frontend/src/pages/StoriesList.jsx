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

const StoriesList = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [storiesArray, setStoriesArray] = useState([]);

  const [bankId, setBankId] = useState("absolutbank");
  const [platform, setPlatform] = useState("ALL PLATFORMS");

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
        }
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchDataAsync();
  }, [bankId, platform]);

  return (
    <>
      <h1>Stories</h1>

      <div className="stories">
        <Button
          text="Добавить"
          type="button"
          color="green"
          icon={<ArrowIcon width="12px" height="12px" />}
          handleOnClick={() => navigate("/stories")}
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
        <StoryPanel storyArray={storiesArray} platform={platform} />
      ) : (
        <h2>Пока нет историй</h2>
      )}

      {loading && <Loader />}
    </>
  );
};

export default StoriesList;
