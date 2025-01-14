import React, {Suspense}  from 'react';
import { BrowserRouter as Router, Switch, Route  } from 'react-router-dom';
import Home from './components/home/Home';
import Search from './components/search/Search';
import GameFile from './components/gamefile/GameFile';
import './App.css';

const App = () => (
  <Router>
    <Suspense fallback={<div>Loading...</div>}>
      <Switch> 
        {/*<Route exact path="/register" component={Register}/>*/}
        <Route path="/search/:searchValue/:platform/:genre" component={Search}/> 
        <Route path="/game/:gameName" component={GameFile}/>
        <Route path="/home" component={Home}/>
        <Route path="/" component={Home}/> 
      </Switch>
    </Suspense>
  </Router>
);

export default App;
