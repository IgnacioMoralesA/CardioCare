Write-Host "Iniciando CardioCare..."

$apiPath = ".\cardioCare-API"
$uiPath = ".\cardioCare-UI\cardioCare"

# Start API
Write-Host "Iniciando API en una nueva ventana..."
Start-Process -FilePath "powershell" -ArgumentList "-NoExit", "-Command", "cd '$apiPath'; mvn spring-boot:run"

# Start UI
Write-Host "Iniciando UI en una nueva ventana..."
Start-Process -FilePath "powershell" -ArgumentList "-NoExit", "-Command", "cd '$uiPath'; ng serve -o"

Write-Host "Comandos ejecutados. Por favor revisa las nuevas ventanas."
