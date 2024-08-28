#!/bin/bash

# Atualizar pacotes e instalar dependências
apt-get update
apt-get install -y curl tar

# Instalar Docker
apt install -y docker.io

#Iniciar servico do Docker
systemctl start docker
systemctl enable docker

# Adicionando o usuário ubuntu ao grupo docker para usar o Docker sem sudo
usermod -aG docker ubuntu

# Criar uma pasta para o GitHub Actions Runner
mkdir /home/ubuntu/actions-runner && cd /home/ubuntu/actions-runner

# Baixar o pacote mais recente do runner
curl -o actions-runner-linux-x64-2.319.1.tar.gz -L https://github.com/actions/runner/releases/download/v2.319.1/actions-runner-linux-x64-2.319.1.tar.gz

# Validar a hash (opcional)
echo "3f6efb7488a183e291fc2c62876e14c9ee732864173734facc85a1bfb1744464  actions-runner-linux-x64-2.319.1.tar.gz" | sha256sum -c

# Extrair o instalador
tar xzf ./actions-runner-linux-x64-2.319.1.tar.gz

# permissoes
chown -R ubuntu:ubuntu /home/ubuntu/actions-runner
chmod -R u+rwX /home/ubuntu/actions-runner
chmod +x /home/ubuntu/actions-runner/config.sh
chmod +x /home/ubuntu/actions-runner/run.sh

sleep 5
# Configurar o runner com valores padrão
sudo -u ubuntu -i bash -c 'cd /home/ubuntu/actions-runner && ./config.sh --url https://github.com/ifmt-cba/sacode --token AP3GLRIINCW3MTYLHUC5OATGZ5AFC --unattended'

# Instalar e iniciar o serviço do runner
sudo -u ubuntu nohup /home/ubuntu/actions-runner/run.sh &

# Defina o script que você deseja executar na terminação
execute_on_termination() {
  echo "A instância está sendo terminada. Executando o script..."
  # Coloque aqui o comando ou script que deseja executar antes da terminação
  ./config.sh remove --token AP3GLRIINCW3MTYLHUC5OATGZ5AFC
}

# Função para verificar o status da instância EC2
check_instance_status() {
  INSTANCE_ID=$(curl -s http://169.254.169.254/latest/meta-data/instance-id)
  INSTANCE_STATUS=$(aws ec2 describe-instance-status --instance-id $INSTANCE_ID --query 'InstanceStatuses[0].InstanceState.Name' --output text --region us-east-1)
  
  echo "Status da instância: $INSTANCE_STATUS"
  
  # Se o status for "shutting-down" ou "terminated", execute o script
  if [[ "$INSTANCE_STATUS" == "shutting-down" || "$INSTANCE_STATUS" == "terminated" ]]; then
    execute_on_termination
    exit 0
  fi
}

# Execute o monitoramento em segundo plano
while true; do
  check_instance_status
  sleep 60 # Verifica a cada 60 segundos
done
