const express = require('express')
const router = express.Router()
const Book = require('../models/book')
const Author = require('../models/author')
const books_srv = require('../service/books_srv')
const imageMimeTypes = ['image/jpeg', 'image/png', 'images/gif']

// async callback: a database queryk async mukodesuek, kell az await, igy olvashatobb a kod !

// All Books Route
router.get('/', async (req, res) => {
  const books = await books_srv.getBooks(req)
  res.json(books)
})


// Create Book Route
router.post('/', async (req, res) => {
  const book = new Book({                             // a sok re.body.akarmi   ezeket a feltelepitett body parser miatt tudja, @RequestBody sprinfgnel
    title: req.body.title,
    author: req.body.author,
    publishDate: new Date(req.body.publishDate),
    pageCount: req.body.pageCount,
    description: req.body.description
  })
  saveCover(book, req.body.cover)                   // stringge alkitott cover kep

  try {
    const newBook = await book.save()
    res.redirect(`books/${newBook.id}`)
  } catch {
    renderNewPage(res, book, true)
  }
})

// Show Book Route
router.get('/:id', async (req, res) => {
  try {
    const book = await Book.findById(req.params.id)       // lekerdezi a bookot id alapjan
                           .populate('author')            // az author fieldet kitolti, de nem author id val, hanem az author osszes tulajdonságával, jelen esetben csak name field van igy csak azzal.
                           .exec()
    res.render('books/show', { book: book })
  } catch {
    res.redirect('/')
  }
})

// Edit Book Route
router.get('/:id/edit', async (req, res) => {
  try {
    const book = await Book.findById(req.params.id)
    renderEditPage(res, book)
  } catch {
    res.redirect('/')
  }
})

// Update Book Route
router.put('/:id', async (req, res) => {
  let book

  try {
    book = await Book.findById(req.params.id)
    book.title = req.body.title
    book.author = req.body.author
    book.publishDate = new Date(req.body.publishDate)
    book.pageCount = req.body.pageCount
    book.description = req.body.description
    if (req.body.cover != null && req.body.cover !== '') {
      saveCover(book, req.body.cover)
    }
    await book.save()
    res.redirect(`/books/${book.id}`)
  } catch {
    if (book != null) {
      renderEditPage(res, book, true)
    } else {
      redirect('/')
    }
  }
})

// Delete Book Page
router.delete('/:id', async (req, res) => {
  let book
  try {
    book = await Book.findById(req.params.id)
    await book.remove()
    res.redirect('/books')
  } catch {
    if (book != null) {
      res.render('books/show', {
        book: book,
        errorMessage: 'Could not remove book'
      })
    } else {
      res.redirect('/')
    }
  }
})

async function renderNewPage(res, book, hasError = false) {
  renderFormPage(res, book, 'new', hasError)
}

async function renderEditPage(res, book, hasError = false) {
  renderFormPage(res, book, 'edit', hasError)
}

async function renderFormPage(res, book, form, hasError = false) {
  try {
    const authors = await Author.find({})                                   // query authors so we can create a params object, and inject it to the ejs page
    const params = {
      authors: authors,
      book: book
    }
    if (hasError) {
      if (form === 'edit') {
        params.errorMessage = 'Error Updating Book'
      } else {
        params.errorMessage = 'Error Creating Book'
      }
    }
    res.render(`books/${form}`, params)                                       // formfields partialsba lehet hasznalni, bar nem, mert ott konkretan author van beinjectalva
  } catch {
    res.redirect('/books')
  }
}

function saveCover(book, coverEncoded) {
  if (coverEncoded == null) return
  const cover = JSON.parse(coverEncoded)
  if (cover != null && imageMimeTypes.includes(cover.type)) {
    book.coverImage = new Buffer.from(cover.data, 'base64')               // springben volt ilyen, bytearraykent lett tarolva a kep
    book.coverImageType = cover.type
  }
}

module.exports = router