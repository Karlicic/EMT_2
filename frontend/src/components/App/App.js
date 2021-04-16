import logo from '../../logo.svg';
import './App.css';
import React, {Component} from 'react';
import {BrowserRouter as Router, Redirect, Route} from "react-router-dom";
import Books from '../Books/BooksList/books'
import Header from '../Header/header';
import BookAdd from '../Books/BookAdd/bookAdd';
import Categories from '../Categories/categories';
import LibraryService from "../../repository/libraryRepository";

class App extends Component {

  constructor(props) {
    super(props);
    this.state = {
      books: []
    }
  }

  render() {
    return (
        <Router>
            <Header/>
            <main>
                <div className={"container"}>
                    <Route path={"/categories"} exact render={() =>
                        <Categories categories={this.state.categories}/>}/>
                    <Route path={"/books/add"} exact render={() =>
                        <BookAdd authors={this.state.authors}
                                 onAddBook={this.addBook}/>}/>
                    <Route path={"/books"} exact render={
                        ()=> <Books books ={this.state.books} onDelete={this.deleteBook}/>
                    }/>
                    <Redirect to={"/books"}/>
                </div>
            </main>

        </Router>
    )
  }

  loadBooks = () => {
    LibraryService.fetchBooks()
        .then((data) => {
          this.setState({
            books: data.data
          })
        });
  }

  deleteBook = (id) =>
  {
      LibraryService.deleteBook(id)
          .then (() =>
          {
              this.loadBooks();
          })
  }

  addBook = (name, category, author, availableCopies) => {
      LibraryService.addBook(name, category, author, availableCopies)
          .then(() =>{
              this.loadBooks();
          });
  }

  componentDidMount() {
    this.loadBooks();
  }

}

export default App;
