import { GoogleReCaptchaProvider } from 'react-google-recaptcha-v3';
import HomePage from './home';

const Home = () => {
  return (
    <>
      <GoogleReCaptchaProvider reCaptchaKey='6LecHQMfAAAAAEttKadXlyW13V9aWQ1pr-c-zQeu'>
        <HomePage />
      </GoogleReCaptchaProvider>
    </>
  );
}

export default Home;