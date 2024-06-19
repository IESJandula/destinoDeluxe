<template>
  <footer>
    <div class="social-icons">
      <a href="#" class="icon"><i class="fab fa-facebook"></i></a>
      <a href="#" class="icon"><i class="fab fa-twitter"></i></a>
      <a href="#" class="icon"><i class="fab fa-instagram"></i></a>
      <a href="#" class="icon"><i class="fab fa-linkedin"></i></a>
    </div>
    <div class="copyright">
      &copy; 2024 Jose Del Pino Castillo
    </div>
  </footer>
</template>

<script setup>

</script>

<style scoped>
footer {
  background-color: #333;
  color: white;
  padding: 20px;
  text-align: center;
  
}

.social-icons {
  margin-bottom: 10px;
}

.icon {
  color: white;
  text-decoration: none;
  margin: 0 10px;
}

.icon:hover {
  color: #55acee; /* Cambia el color al pasar el ratón */
}

.copyright {
  font-size: 14px;
}
</style>
