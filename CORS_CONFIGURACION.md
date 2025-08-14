# Configuración CORS - FacturitaX API

## 🎯 Problema solucionado
Se ha configurado CORS para permitir que tu frontend de Angular pueda consumir la API sin errores.

## 🔧 Configuraciones agregadas

### 1. CorsConfig global
- Archivo: `facturita-config/src/main/java/com/cna/facturita/config/config/CorsConfig.java`
- Permite peticiones desde:
  - `http://localhost:4200` (Angular por defecto)
  - `http://localhost:3000` (React/Next.js)
  - `http://127.0.0.1:4200`
  - `http://127.0.0.1:3000`

### 2. SecurityConfig actualizado
- Configuración de CORS integrada con Spring Security
- Permite preflight requests (OPTIONS)
- Headers de autorización permitidos

### 3. Anotaciones @CrossOrigin
- Agregadas a `AuthController` y `UsuarioController`
- Protección adicional a nivel de controlador

## 🚀 Endpoints disponibles para tu frontend

### Registro de usuario (público):
```bash
POST http://localhost:8080/auth/registro
Content-Type: application/json

{
  "nombre": "Admin Test",
  "email": "admin@test.com",
  "password": "123456"
}
```

### Login (público):
```bash
POST http://localhost:8080/auth/login
Content-Type: application/json

{
  "email": "admin@test.com", 
  "password": "123456"
}
```

### Verificar usuarios existentes (público):
```bash
GET http://localhost:8080/auth/tiene-usuarios
```

### Gestión de usuarios (requiere JWT):
```bash
GET http://localhost:8080/usuarios
Authorization: Bearer {jwt-token}
```

## 💡 Para tu frontend de Angular

### Ejemplo de servicio Angular:
```typescript
import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8080';
  
  constructor(private http: HttpClient) {}
  
  // Registro público
  registro(usuario: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/registro`, usuario);
  }
  
  // Login público
  login(credenciales: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/auth/login`, credenciales);
  }
  
  // Peticiones con JWT
  getUsuarios(token: string): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get(`${this.apiUrl}/usuarios`, { headers });
  }
}
```

## 🔥 Flujo recomendado

1. **Verificar si hay usuarios**: `GET /auth/tiene-usuarios`
2. **Si no hay usuarios, registrar el primero**: `POST /auth/registro`
3. **Hacer login**: `POST /auth/login`
4. **Usar el token JWT** para peticiones autenticadas

## ⚡ Nota importante
Asegúrate de que tu frontend Angular esté corriendo en `http://localhost:4200` o ajusta las URLs en la configuración CORS según tu puerto.
