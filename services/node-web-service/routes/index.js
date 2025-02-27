var express = require('express');
var router = express.Router();

router.get('/hello', function(req, res, next) {
  res.json({message: 'Hello, World!'});
});

module.exports = router;
