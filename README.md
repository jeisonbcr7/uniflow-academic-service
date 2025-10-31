# Uniflow Academic Service

## Variables de Entorno .env

Nota: Cambiar solo las de conexión a PostgreSQL

```dotenv
# ============================================
# PostgreSQL Database Configuration
# ============================================
DB_HOST=
DB_PORT=
DB_NAME=
DB_USER=
DB_PASSWORD=

# ============================================
# Server Configuration
# ============================================
SERVER_PORT=8080
SERVER_SERVLET_CONTEXT_PATH=/academic/v1

# ============================================
# Spring Profiles
# ============================================
SPRING_PROFILES_ACTIVE=dev

# ============================================
# Logging
# ============================================
LOGGING_LEVEL_ROOT=INFO
LOGGING_LEVEL_COM_UNIFLOW=DEBUG
LOGGING_LEVEL_ORG_HIBERNATE_SQL=DEBUG

# ============================================
# CORS Configuration
# ============================================
CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:8080
CORS_ALLOWED_METHODS=GET,POST,PUT,DELETE,PATCH,OPTIONS
CORS_ALLOWED_HEADERS=*
CORS_ALLOW_CREDENTIALS=true

# ============================================
# Application Configuration
# ============================================
APP_NAME=UniFlow Academic Service
APP_VERSION=1.0.0
APP_DESCRIPTION=RESTful API for managing academic data

# ============================================
# Swagger/OpenAPI
# ============================================
SPRINGDOC_SWAGGER_UI_ENABLED=true
SPRINGDOC_API_DOCS_ENABLED=true
```