import React from 'react';
import { Formik, Form } from 'formik';
import Modal from './../components/ui/Modal/index';
import FormField from './../components/FormField/index';
import Button from './../components/ui/Button/index';
import { useAuth } from './../components/Security/AuthProvider'; // Импортируем правильный хук


const Login = () => {

    const auth = useAuth();
    const handleSubmitEvent = (values) => {
      if (values.login !== "" && values.password !== "") {
        auth.loginAction(values);
        return;
      }
      alert("pleae provide a valid input");
    };

  return (
    <Modal>
      <Formik
        initialValues={{
          login: "",
          password: ""
        }}
        onSubmit={handleSubmitEvent}
      >
        {({ values, handleChange }) => (
          <Form>
            <FormField
              labelTitle="Login"
              name="login"
              value={values.login}
              type="text"
              handleChange={handleChange}
            />
            <FormField
              labelTitle="Password"
              name="password"
              type="password"
              value={values.password}
              handleChange={handleChange}
            />
            <Button
              text="Войти"
              type="submit"
              color="red"
            />
          </Form>
        )}
      </Formik>
    </Modal>
  );
};

export default Login;
